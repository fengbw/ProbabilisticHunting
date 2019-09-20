import random

not_found_when_in = {0.1, 0.3, 0.7, 0.9}
found_when_in = {0.9, 0.7, 0.3, 0.1}

def map(size):
    map = [[None] * size for i in range(size)]
    for i in range(size):
        for j in range(size):
            map[i, j] = terrain()
    return m

def terrain():
    type = random.rand()
    if type < 0.2:
        return 0
    if p < 0.5:
        return 1
    if p < 0.8:
        return 2
    if p <= 1.0:
        return 3

def not_found_when_in(type):
    return not_found_when_in[type]

def Target_of_Type(map, type):
    size = len(map)
    a = random.randint(0, size - 1)
    b = random.randint(0, size - 1)
    while map[a, b] != type:
        a = random.randint(0, dim - 1)
        b = random.randint(0, dim - 1)
    return (a, b)

def distance(size, row, col):
    dis = [[None] * size for i in range(size)]
    for x1 in range(size):
        for y1 in range(size):
            dis[x1, y1] = abs(x1 - row) + abs(y1 - col)
    return dis

def intial_belief(size):
    initial = 1 / (size * size)
    belief = [[initial] * size for i in range(size)]
    return belief

def choose_target(size):
    row = random.randint(1, size + 1)
    col = random.randint(1, size + 1)
    return (row, col)

def update_belief(map, belief, row, col):
    belief[row, col] *= (not_found_when_in(map[row, col]))
    normalization = sum(belief)
    belief = belief / normalization
    return belief

def probability_found(map, belief):
    size = len(map)
    probability = [[None] * size for i in range(size)]
    for i in range(size):
        for j in range(size):
            probability[i, j] = belief[i, j] * found_when_in[map[i, j]]
    return probability
