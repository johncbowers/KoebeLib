import numpy as np
import matplotlib.pyplot as plt
from matplotlib import collections as mc

verts = np.loadtxt('../../../../verts', dtype='float', delimiter=',')
edges = np.loadtxt('../../../../edges', dtype='int', delimiter=',')
config = np.loadtxt('../../../../config', dtype='int')

edges = edges[(edges[:,0] < config) & (edges[:,1] < config)]

data = []
for x in range(0, edges.shape[0]):
    data.append([(verts[edges[x,0],0], verts[edges[x,0],1]), (verts[edges[x,1],0], verts[edges[x,1],1])])

lc = mc.LineCollection(data)
fig, ax = plt.subplots()
ax.add_collection(lc)
ax.autoscale()
fig.show()
plt.show()
