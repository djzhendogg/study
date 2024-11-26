from SVM import SVMClassifier
import random
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.metrics import mean_squared_error
from sklearn.metrics import f1_score

from sklearn.decomposition import PCA
from sklearn.preprocessing import MinMaxScaler

from sklearn.datasets import load_breast_cancer


X, y = load_breast_cancer(return_X_y=True)
scaler = MinMaxScaler()
X = scaler.fit_transform(X)
pca = PCA(n_components=2)
X = pca.fit_transform(X)
y[y == 0] = -1
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

model = SVMClassifier(kernel="polynom", polynom_degree=1, lr=0.01, C=5)
model.fit(X_train, y_train)
y_pred = model.predict(X_test)
accuracy_score(y_test, y_pred)
