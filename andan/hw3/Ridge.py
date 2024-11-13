import numpy as np
from sklearn.metrics import accuracy_score
from sklearn.metrics import mean_squared_error


class RidgeRegression:
    def __init__(self):
        self.x_train = None
        self.y_train = None
        self.n = None
        self.theta = None

    def fit(self, x_train, y_train):
        self.x_train = x_train
        self.y_train = y_train
        self.n = x_train.shape[1]

    def empirical_risk(self, y_true, y_pred, tau, theta):
        return np.sum(np.square(y_pred - y_true)) + tau * np.sum(np.square(theta))

    def calculate_param_vector(self, tau):
        if tau == 0:
            return np.linalg.pinv(self.x_train) @ self.y_train
        else:
            return np.linalg.inv(self.x_train.T @ self.x_train + tau * np.eye(self.n)) @ self.x_train.T @ self.y_train

    def predict(self, x_test, tau):
        theta = self.calculate_param_vector(tau)
        return theta @ x_test.T, theta

    def predict_best(self, x_test, y_test, max_tau):
        min_error = np.inf
        for tau in np.linspace(0, max_tau, 500):
            y_pred, theta = self.predict(x_test, tau)
            error = self.empirical_risk(y_test, y_pred, tau, theta)
            if error < min_error:
                min_error = error
                self.theta = theta

        return self.theta @ x_test.T

    def get_params(self):
        return self.theta

    def score(self, y_test, y_pred):
        return mean_squared_error(y_test, y_pred)


class RidgeClassifier(RidgeRegression):
    def __init__(self):
        super().__init__()

    def fit(self, x_train, y_train):
        super().fit(x_train, y_train)

    def predict_best(self, x_test, y_test, max_tau):
        y_pred_row = super().predict_best(x_test, y_test, max_tau)
        return np.sign(y_pred_row)

    def get_params(self):
        return super().get_params()

    def score(self, y_test, y_pred):
        return accuracy_score(y_test, y_pred)
