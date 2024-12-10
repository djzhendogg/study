import numpy as np


class DecisionTree:
    def __init__(
            self,
            min_samples_split=2,
            max_depth=5,
            min_samples_leaf=10,
            max_features=20
    ):
        self.min_samples_split = min_samples_split
        self.max_depth = max_depth
        self.min_samples_leaf = min_samples_leaf
        self.max_features = max_features
        self.root = None

    @staticmethod
    def calc_metric(parent, left, right):
        def entropy(y):
            res = 0
            classes = np.unique(y)
            for cl in classes:
                pl = len(y[y == cl]) / len(y)
                res -= pl * np.log2(pl)
            return res

        s_entropy = entropy(parent)
        s_left_s = len(left) / len(parent)
        s_right_s = len(right) / len(parent)
        weighted_entropy = s_left_s * entropy(left) + s_right_s * entropy(right)
        return s_entropy - weighted_entropy

    def define_node(self, dataset, num_features):
        sample_vertex = Vertex(metric=-1)
        for f_id in range(num_features):
            thresholds = np.unique(dataset[:, f_id])
            for th in thresholds:
                left = []
                right = []
                for row in dataset:
                    if row[f_id] <= th:
                        left.append(row)
                    else:
                        right.append(row)
                left, right = np.array(left), np.array(right)

                if len(left) >= self.min_samples_leaf and len(right) >= self.min_samples_leaf:
                    i_gain = self.calc_metric(dataset[:, -1], left[:, -1], right[:, -1])
                    if i_gain > sample_vertex.metric:
                        sample_vertex = Vertex(f_id, th, i_gain, left, right)

        if sample_vertex.metric > 0:
            return sample_vertex
        else:
            return None

    def build(self, data, current_depth=0):
        n_samples, n_features = data[:, :-1].shape
        if n_samples >= self.min_samples_split and current_depth <= self.max_depth:

            if n_features > self.max_features:
                n_features = self.max_features

            new_node = self.define_node(data, n_features)

            if new_node is not None:
                left = self.build(new_node.left, current_depth + 1)
                right = self.build(new_node.right, current_depth + 1)
                return Vertex(new_node.feature, new_node.threshold, new_node.metric, left, right)

        vals = data[:, -1]
        return Vertex(value=max(list(vals), key=list(vals).count))


    def fit(self, X_y_train):
        self.root = self.build(X_y_train)

    def predict(self, X_test):
        return np.array([self.decision_function(i, self.root) for i in X_test])

    def decision_function(self, x, vertex):
        if vertex.value is None:
            if x[vertex.feature] <= vertex.threshold:
                return self.decision_function(x, vertex.left)
            else:
                return self.decision_function(x, vertex.right)
        else:
            return vertex.value



class Vertex:
    def __init__(
            self,
            feature=None,
            threshold=None,
            metric=None,
            left=None,
            right=None,
            value=None
    ):
        self.feature = feature
        self.threshold = threshold
        self.left = left
        self.right = right
        self.metric = metric
        self.value = value