import pandas as pd
import numpy as np
from sklearn.preprocessing import normalize
from sklearn.ensemble import RandomForestRegressor
from sklearn.externals import joblib
data=pd.read_csv("atlantic.csv")

np.random.seed(0)

X=data.iloc[:,0:4].values
y_ne=data.iloc[:,4].values
y_se=data.iloc[:,5].values
y_sw=data.iloc[:,6].values
y_nw=data.iloc[:,7].values

s = np.arange(X.shape[0])
np.random.shuffle(s)
X = X[s]
y_ne = y_ne[s]
y_se = y_se[s]
y_sw = y_sw[s]
y_nw = y_nw[s]

#X = normalize(X, axis = 0)
#print(y.mean())
#y = y / np.linalg.norm(y)

train=int(0.9*len(data))
test=int(0.1*len(data))

x_train=X[:train]
x_test=X[train:]

y_train_ne=y_ne[:train]
y_train_se=y_se[:train]
y_train_sw=y_sw[:train]
y_train_nw=y_nw[:train]


y_test_ne=y_ne[train:]
y_test_se=y_se[train:]
y_test_sw=y_sw[train:]
y_test_nw=y_nw[train:]

clf_ne = RandomForestRegressor(n_estimators=50)
clf_se = RandomForestRegressor(n_estimators=50)
clf_sw = RandomForestRegressor(n_estimators=50)
clf_nw = RandomForestRegressor(n_estimators=50)

clf_ne.fit(x_train, y_train_ne)
clf_se.fit(x_train, y_train_se)
clf_sw.fit(x_train, y_train_sw)
clf_nw.fit(x_train, y_train_nw)


ypred_ne=clf_ne.predict(x_test)
ypred_se=clf_se.predict(x_test)
ypred_sw=clf_sw.predict(x_test)
ypred_nw=clf_nw.predict(x_test)

acc_ne = 0
acc_se = 0
acc_sw = 0
acc_nw = 0

for i in range(len(ypred_ne)):
	acc_ne += (ypred_ne[i] - y_test_ne[i])**2
	acc_se += (ypred_se[i] - y_test_se[i])**2
	acc_sw += (ypred_sw[i] - y_test_sw[i])**2
	acc_nw += (ypred_nw[i] - y_test_nw[i])**2

acc_ne /= len(y_ne)
acc_se /= len(y_ne)
acc_sw /= len(y_ne)
acc_nw /= len(y_ne)

acc_ne = acc_ne ** 0.5
acc_se = acc_se ** 0.5
acc_sw = acc_sw ** 0.5
acc_nw = acc_nw ** 0.5

print(acc_ne, acc_se, acc_sw, acc_nw)

from sklearn.externals import joblib
joblib.dump(clf_ne, 'model_ne.pkl')
joblib.dump(clf_se, 'model_se.pkl')
joblib.dump(clf_sw, 'model_sw.pkl')
joblib.dump(clf_nw, 'model_nw.pkl')
