import numpy as np
from sklearn.metrics import accuracy_score


class GradientClassifier:
    def __init__(
            self,
            empirical_risk='piecewise',
            lr=0.1,
            tau1_range=np.linspace(0, 2, 500),
            tau2_range=np.linspace(0, 2, 500)
    ):
        # risks = {
        #     "piecewise": lambda y_true, y_pred, a, b, theta: -1 * np.sum(y_true * y_pred)
        #                        + a * np.sum(np.square(theta)) + b * np.sqrt(np.sum(np.square(theta))),
        #     "exponential": lambda y_true, y_pred, a, b, theta: np.exp(-1 * np.sum(y_true * y_pred))
        #                        + a * np.sum(np.square(theta)) + b * np.sqrt(np.sum(np.square(theta))),
        #     "square": lambda y_true, y_pred, a, b, theta: np.square(1 - np.sum(y_true * y_pred))
        #                        + a * np.sum(np.square(theta)) + b * np.sqrt(np.sum(np.square(theta))),
        # }
        risks = {
            "piecewise": lambda y_true, y_pred: -1 * np.sum(y_true * y_pred),
            "exponential": lambda y_true, y_pred: np.exp(-1 * np.sum(y_true * y_pred)),
            "square": lambda y_true, y_pred: np.square(1 - np.sum(y_true * y_pred))
        }
        self.empirical_risk = risks[empirical_risk]
        self.max_iter = 10000
        self.best_error = np.inf
        self.lr = lr
        self.tau1_range = tau1_range
        self.tau2_range = tau2_range
        self.x_train = None
        self.y_train = None
        self.best_params = None
        self.best_tau1 = None
        self.best_tau2 = None

    def calculate_param_vector(self, tau1, tau2):
        best_local_error = np.inf
        theta = np.zeros(self.x_train.shape[1], dtype=float)
        best_local_params = None
        for i in range(self.max_iter):
            r = np.random.randint(0, len(self.x_train))
            grad = (np.dot(self.x_train[r], theta) - self.y_train[r]) * self.x_train[r]
            alpha = (np.dot(theta, self.x_train[r]) - self.y_train[r]) / np.dot(grad, self.x_train[r])

            theta = theta * (1 - alpha) - self.lr * alpha * grad
            # err = self.empirical_risk(self.y_train, self.x_train @ self.init_theta, tau1, tau2, theta)
            err = self.empirical_risk(self.y_train, self.x_train @ theta)
            if best_local_error > err:
                best_local_error = err
                best_local_params = theta

        if self.best_error > best_local_error:
            self.best_params = best_local_params
            self.best_error = best_local_error
            self.best_tau1 = tau1
            self.best_tau2 = tau2
        return best_local_error, best_local_params

    def fit(self, X_train, y_train):
        self.x_train = X_train
        self.y_train = y_train
        self.calculate_best_params()

    def calculate_best_params(
            self
    ):
        # for i in self.tau1_range:
        #     for j in self.tau2_range:
        #         self.calculate_param_vector(self.init_theta, i, j)
        i = 0
        j = 0
        self.calculate_param_vector(i, j)

    def predict(self, X_test):
        return np.sign(X_test @ self.best_params)

    def score(self, y_test, y_pred):
        return accuracy_score(y_test, y_pred)

