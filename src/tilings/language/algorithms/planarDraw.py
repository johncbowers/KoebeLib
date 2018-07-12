import numpy as np
import matplotlib.pyplot as plt
from matplotlib import collections as mc

verts = np.loadtxt('chair.verts', dtype='float', delimiter=',')
edges = np.loadtxt('chair.edges', dtype='int', delimiter=',')

#edges = edges[(edges[:,0] < 12545) & (edges[:,1] < 12545)]

data = []
for x in range(0, edges.shape[0]):
    data.append([(verts[edges[x,0],0], verts[edges[x,0],1]), (verts[edges[x,1],0], verts[edges[x,1],1])])

lc = mc.LineCollection(data)
fig, ax = plt.subplots()
ax.add_collection(lc)
ax.autoscale()
fig.show()
