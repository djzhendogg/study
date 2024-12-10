import numpy as np
from .Decision_tree import DecisionTree
from sklearn.metrics import accuracy_score


class RandomForest:
    def __init__(
            self,
            n_estimators=100,
            min_samples_split=2,
            max_depth=5,
            min_samples_leaf=10,
            batch_size=100
    ):
        self.n_estimators = n_estimators
        self.min_samples_split = min_samples_split
        self.max_depth = max_depth
        self.min_samples_leaf = min_samples_leaf
        self.batch_size = batch_size

        self.max_features = 0
        self.n_features = 0
        self.trees = []
        self.X_train = None
        self.y_train = None


    def fit(self, X_train, y_train):
        self.X_train = X_train
        self.y_train = y_train
        np.random.seed(1)
        n_samples = self.X_train.shape[0]
        self.n_features = self.X_train.shape[1]
        self.max_features = int(np.sqrt(self.X_train.shape[1]))
        for _ in range(self.n_estimators):

            tree = DecisionTree(
                min_samples_split=self.min_samples_split,
                max_depth=self.max_depth,
                min_samples_leaf=self.min_samples_leaf,
                max_features=self.max_features
            )
            ids_row = np.random.choice(n_samples, self.batch_size, replace=True)
            ids_cols = np.random.choice(self.n_features, self.max_features, replace=True)

            batch = self.take_features(self.X_train[ids_row], ids_cols)
            tree.fit(np.concatenate((batch, y_train[ids_row]), axis=1))
            self.trees.append({"tree": tree, "cols": ids_cols})

    @staticmethod
    def take_features(data, cols):
        to_concat = ()
        for col in cols:
            to_add = data[:, col].reshape(-1, 1)
            to_concat = to_concat + (to_add,)
        return np.concatenate(to_concat, axis=1)

    def predict(self, X_test):
        full_votes = np.array(
            [
                algo.get("tree").predict(
                    self.take_features(X_test, algo.get("cols"))
                ) for algo in self.trees
            ]
        )

        full_votes = np.swapaxes(full_votes, 0, 1)
        return np.array([max(list(vote), key=list(vote).count) for vote in full_votes])

    def get_train_score(self):
        y_pred_train = self.predict(self.X_train)
        return accuracy_score(self.y_train, y_pred_train)

    def get_test_score(self, X_test, y_test):
        y_pred_train = self.predict(X_test)
        return accuracy_score(y_test, y_pred_train)
