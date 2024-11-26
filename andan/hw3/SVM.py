import random
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.metrics import mean_squared_error
from sklearn.metrics import f1_score

from sklearn.decomposition import PCA

from sklearn.datasets import load_breast_cancer


class SVMClassifier:
    def __init__(
            self,
            kernel='square',
            C=0.1,
            polynom_degree=2,
            lr=0.01,
            max_iter=500
    ):
        all_kernels = {
            "linear": lambda a, b, d: a.dot(b.T),
            "polynom": lambda a, b, d: (1 + a @ b.T) ** d,
            "square": lambda a, b, d: (a @ b.T)**2
        }
        self.iter = max_iter

        self.kernel = all_kernels[kernel]

        self.C = C
        self.d = polynom_degree
        self.lr = lr

        self.x_train = None
        self.y_train = None
        self.kernels = None
        self.alphas = None

        self.n = 0
        self.b = 0
        self.loss = 0
        self.history_loss = []
        self.history_accuracy = []


    def fit(self, X_train, y_train):
        self.x_train = X_train
        self.y_train = y_train
        self.n = self.x_train.shape[0]
        b = np.where(self.y_train < 0)[0]
        a = np.where(self.y_train > 0)[0]
        self.alphas = np.full(self.n, 0.0)
        alphas = np.full(self.n, 0.0)
        self.kernels = np.outer(self.y_train, self.y_train) * self.kernel(self.x_train, self.x_train, self.d)
        for i in range(self.iter):
            grad = 1 - self.kernels @ alphas
            alphas += self.lr * grad
            alphas[alphas > self.C] = self.C
            alphas[alphas < 0] = 0

            if abs(np.sum(alphas[b]) - np.sum(alphas[a])) > 10e-9:
                if np.sum(alphas[b]) > np.sum(alphas[a]):
                    between = np.sum(alphas[b]) - np.sum(alphas[a])
                    for a_index in a:
                        alphas[a_index] = min(self.C, alphas[a_index] + between/alphas[a].shape[0])
                elif np.sum(alphas[a]) > np.sum(alphas[b]):
                    between = np.sum(alphas[a]) - np.sum(alphas[b])
                    for b_index in b:
                        alphas[b_index] = min(self.C, alphas[b_index] + between/alphas[b].shape[0])

            loss = np.sum(alphas) - 0.5 * np.sum(
                np.outer(alphas, alphas) * self.kernels)
            if loss > self.loss:
                self.alphas = alphas
                self.loss = loss

            self.history_loss.append(loss)
            y_pred = np.sign(self.decision_alpha(self.x_train, self.x_train, alphas))
            noe_accuracy = accuracy_score(y_pred, self.y_train)
            self.history_accuracy.append(noe_accuracy)

        significants = np.where(self.alphas > 0 & (self.alphas <= self.C))[0]

        b_list = []
        for i in significants:
            b_list.append(self.y_train[i] - (self.alphas * self.y_train).dot(self.kernel(self.x_train, self.x_train[i], self.d)))

        self.b = np.mean(b_list)
        return self

    def predict(self, x_test):
        res = self.decision(self.x_train, x_test)
        return np.sign(res)

    def decision(self, z, x_test):
        return (self.alphas * self.y_train).dot(self.kernel(z, x_test, self.d)) + self.b

    def decision_alpha(self, z, x_test, alphas):
        return (alphas * self.y_train).dot(self.kernel(z, x_test, self.d)) + self.b