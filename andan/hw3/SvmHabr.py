import numpy as np
import pandas as pd
import random
from sklearn.datasets import make_blobs, make_moons, make_circles
from sklearn.metrics import mean_squared_error
from sklearn.svm import SVC
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

class SVM_Dual:

    def __init__(self, kernel='poly', degree=2, sigma=0.1, epoches=1000, learning_rate=0.001):
        self.alpha = None
        self.b = 0
        self.degree = degree
        self.C = 1
        self.sigma = sigma
        self.epoches = epoches
        self.learning_rate = learning_rate

        if kernel == 'poly':
            self.kernel = self.polynomial_kernal  # for polynomial kernal
        elif kernel == 'rbf':
            self.kernel = self.gaussian_kernal  # for guassian

    def polynomial_kernal(self, X, Z):
        return (1 + X.dot(Z.T)) ** self.degree  # (c + X.y)^degree

    def gaussian_kernal(self, X, Z):
        return np.exp(-(1 / self.sigma ** 2) * np.linalg.norm(X[:, np.newaxis] - Z[np.newaxis, :],
                                                              axis=2) ** 2)  # e ^-(1/ σ2) ||X-y|| ^2

    def train(self, X, y):
        self.X = X
        self.y = y
        self.alpha = np.random.random(X.shape[0])
        self.b = 0
        self.ones = np.ones(X.shape[0])

        y_mul_kernal = np.outer(y, y) * self.kernel(X, X)  # yi yj K(xi, xj)

        for i in range(self.epoches):
            gradient = self.ones - y_mul_kernal.dot(self.alpha)  # 1 – yk ∑ αj yj K(xj, xk)

            self.alpha += self.learning_rate * gradient  # α = α + η*(1 – yk ∑ αj yj K(xj, xk)) to maximize
            self.alpha[self.alpha > self.C] = self.C  # 0<α<C
            self.alpha[self.alpha < 0] = 0  # 0<α<C

            loss = np.sum(self.alpha) - 0.5 * np.sum(
                np.outer(self.alpha, self.alpha) * y_mul_kernal)  # ∑αi – (1/2) ∑i ∑j αi αj yi yj K(xi, xj)

        alpha_index = np.where((self.alpha) > 0 & (self.alpha < self.C))[0]

        # for intercept b, we will only consider α which are 0<α<C 
        b_list = []
        for index in alpha_index:
            b_list.append(y[index] - (self.alpha * y).dot(self.kernel(X, X[index])))

        self.b = np.mean(b_list)  # avgC≤αi≤0{ yi – ∑αjyj K(xj, xi) }

    def predict(self, X):
        return np.sign(self.decision_function(X))

    def score(self, X, y):
        y_hat = self.predict(X)
        return np.mean(y == y_hat)

    def decision_function(self, X):
        return (self.alpha * self.y).dot(self.kernel(self.X, X)) + self.b
