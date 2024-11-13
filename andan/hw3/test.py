from Gradient import GradientClassifier
from sklearn.datasets import load_breast_cancer
from sklearn.model_selection import train_test_split


X, y = load_breast_cancer(return_X_y=True)
y[y == 0] = -1
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)
model = GradientClassifier()
model.fit(X_train, y_train)
y_pr = model.predict(X_test)
model.score(y_test, y_pr)