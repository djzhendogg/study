import numpy as np
from .Decision_tree import DecisionTree
from sklearn.metrics import accuracy_score

class GradientBoosting():
    def __init__(
            self,
            n_estimators = 10,
            learning_rate = 0.01,
            min_samples_split=10,
            max_depth=2,
            min_samples_leaf=10,
            max_features=20
    ):
        self.n_estimators = n_estimators
        self.learning_rate = learning_rate
        self.min_samples_split = min_samples_split
        self.max_depth = max_depth
        self.min_samples_leaf = min_samples_leaf
        self.max_features = max_features

        self.X_train = None
        self.y_train = None
        self.trees = []

        for _ in range(n_estimators):
            tree = DecisionTree(
                min_samples_split=self.min_samples_split,
                max_depth=self.max_depth,
                min_samples_leaf=self.min_samples_leaf,
                max_features=self.max_features
            )
            self.trees.append(tree)

    def fit(self, X_train, y_train):
        self.X_train = X_train
        self.y_train = y_train
        initial_vals = np.full(np.shape(self.y_train), np.mean(self.y_train, axis=0))
        self.trees[0].fit(np.concatenate((self.X_train, initial_vals), axis=1))
        y_pred = self.trees[0].predict(self.X_train)
        for t in range(1, self.n_estimators):
            gradient = self.calculate_loss(self.y_train, y_pred)
            self.trees[t].fit(np.concatenate((self.X_train, gradient), axis=1))
            at = self.trees[t].predict(self.X_train)
            y_pred -= np.multiply(self.learning_rate, at)

    @staticmethod
    def calculate_loss(y, p):
        p = np.clip(p, 1e-12, 1 - 1e-12)
        return - y * np.log(p) - (1 - y) * np.log(1 - p)

    def predict(self, X):
        y_pred = np.array([])
        for tree in self.trees:
            update = tree.predict(X)
            update = np.multiply(self.learning_rate, update)
            y_pred = update if not y_pred.any() else update - y_pred
        return np.sign(y_pred)

    def get_train_score(self):
        y_pred_train = self.predict(self.X_train)
        return accuracy_score(self.y_train, y_pred_train)

    def get_test_score(self, X_test, y_test):
        y_pred_train = self.predict(X_test)
        return accuracy_score(y_test, y_pred_train)