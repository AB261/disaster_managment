import tensorflow
import pandas as pd
import numpy as np
from sklearn.preprocessing import normalize
from sklearn.ensemble import RandomForestRegressor
from sklearn.externals import joblib
data=pd.read_csv("atlantic.csv")

X=data.iloc[:,0:4].values
y=data.iloc[:,4].values

X = normalize(X, axis = 0)
print(y.mean())
#y = y / np.linalg.norm(y)

train=int(0.8*len(data))
test=int(0.3*len(data))

x_train=X[:train]
x_test=X[train:]

y_train=y[:train]
y_test=y[train:]

clf = RandomForestRegressor(n_estimators=30)
clf.fit(x_train, y_train)
y2=clf.predict(x_test)

from sklearn.externals import joblib
joblib.dump(classifier, 'model.pkl')

