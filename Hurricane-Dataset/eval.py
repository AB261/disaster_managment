import numpy as np
from sklearn.ensemble import RandomForestRegressor
from sklearn.externals import joblib

a = 30
b = 80
c = 1000
d = 45

x_test = np.array([a,b,c,d]).reshape(1,4)

clf_ne = joblib.load('model_ne.pkl') 
clf_se = joblib.load('model_se.pkl') 
clf_sw = joblib.load('model_sw.pkl') 
clf_nw = joblib.load('model_nw.pkl') 

ypred_ne=str(abs(clf_ne.predict(x_test)[0]))
ypred_se=str(abs(clf_se.predict(x_test)[0]))
ypred_sw=str(abs(clf_sw.predict(x_test)[0]))
ypred_nw=str(abs(clf_nw.predict(x_test)[0]))




