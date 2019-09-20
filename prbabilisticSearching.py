import random
import initializer as initial

def get_max(probability):
    max_probability = max(probability)
    length = len(probability)
    index = []
    for i in range(length):
        for j in range(length):
            if probability[i, j] == max_probability:
                l.append((i, j))
    a = random.randint(0, len(index) - 1)
    return index[a]

def rule_1(map, belief, target):
    searches = 0
    cell = get_max(belief_matrix)
    while (target[0], target[1]) != cell:
        belief = initial.update_belief_matrix(map, belief, cell[0], cell[1])
        searches += 1
        next_step = get_max(belief)
    return searches


def rule_2(map, belief, target):
    earches = 0
    probability_found = initial.probability_found(map, belief)
    cell = get_max(probability_found)
    while (target[0], target[1]) != cell:
        belief = initial.update_belief(map, belief, cell[0], cell[1])
        searches += 1
        prob_found_matrix = initial.probability_found(map, belief)
        cell = get_max(probability_found)
    return searches

def ques_3():
    for type in [0, 1, 2, 3]:
        searches_1, searches_2 = 0, 0
        iterations = 100
        size = 50

        for i in range(iterations):
            if i%10 == 0:
                print(i)
            map = initial.map(size)
            target = initial.Target_of_Type(map, type)
            belief_matrix = initial.intial_belief(size)
            searches_1 += rule_1(map, belief, target)
            searches_2 += rule_2(map, belief, target)
        print("type :: " + str(type))
        print("Average number of searches rule-1 and rule-2 respectively: ", str(searches_1 / iterations) + "  ,  "+str(searches_2 / iterations) + "\n")

def probability_distance(map, belief, target):
    cell = get_max(belief)
    size = len(map)
    dis = initial.distance(cell, target[0], target[1])
    total_dis = 0
    for i in range(size):
        for j in range(size):
            if (i, j) != (cell[0], cell[1]):
                total_dis += dis[i, j]
    for i in range(size):
        for j in range(size):
            belief[i, j] += dis[i, j] / total_dis
    return belief

def rule_1_dis(map, belief, target, simple = True):
    searches = 0
    cell = get_max(belief)
    size = len(map)
    dis = [[0] * size for i in range(size)]
    while (target[0], target[1]) != cell:
        belief = initial.update_belief(map, belief, cell[0], cell[1])
        searches += dis[cell[0], cell[1]]
        if simple == False:
            cell = get_max_index(probability_distance(map, belief, target))
        else:
            cell = get_max_index(belief)
    return earches

def rule_2_dis(board, belief, target, simple = True):
    searches = 0
    probability_found = initial.probability_found(map, belief)
    cell = get_max(probability_found)
    size = len(map)
    dis = [[0] * size for i in range(size)]
    while (target[0], target[1]) != cell:
        belief = initial.update_belief(map, belief, cell[0], cell[1])
        searches += dis[cell[0], cell[1]]
        probability_found = initial.probability_found(map, belief)
        dis = initial.distance(size, cell[0], cell[1])
        if simple == False:
            cell = get_max_index(probability_distance(map, belief, target))
        else:
            cell = get_max_index(probability_found)
    return searches

def ques_4(board, belief, target):
    searches = 0
    probability_found = initial.probability_found(map, belief)
    cell = get_max(probability_found)
    while target != cell:
        belief = initial.update_belief(map, belief, cell[0], cell[1])
        searches += 1
        probability_found = initial.probability_found(map, belief)
        cell = get_max(probability_found)
    return searches

def ques_4(board, belief, target):
    for type in [0, 1, 2, 3]:
        searches_1, searches_2 = 0, 0
        searches_1_dis, searches_2_dis = 0, 0
        iterations = 100
        size = 50
        for i in range(iterations):
            map = initial.map(size)
            target = initial.Target_of_Type(map,type)
            belief_matrix = generate_intial_belief_matrix(dim)
            searches_1 += rule_1_dis(map, belief, target, False)
            searches_1_dis += rule_1_dis(map, belief, target)
            searches_2 += rule_2_dis(map, belief, target, False)
            searches_2_dis += rule_2_dis(map, belief, target)
        print("type :: " + str(type))
        print("Average number of searches rule-1 and rule-2 respectively: ", str(searches_1 / iterations) + "  ,  "+str(searches_2 / iterations)+"\n")
        print("Average number of searches rule-1 and rule-2 respectively: ", str(searches_1_dis / iterations) + "  ,  " + str(searches_2_dis / iterations) + "\n")

def moving(size, target, map):
    row = target[0]
    col = target[1]
    type1 = map[a, b]
    index1 = []
    index2 = []
    index1.append([row - 1, col])
    index1.append([row + 1, col])
    index1.append([row, col - 1])
    index1.append([row, col + 1])
    for index in index1:
        if valid(size, index[0], index[1]):
            index2.append(l)
    choose = random.randint(0, len(index2))
    type2 = board[index2[choose][0], index2[choose][1]]
    return index2[choose],(type1, type2)

def get_neighbs(size, row, col):
    index1 = []
    index2 = []
    index1.append([row - 1, col])
    index1.append([row + 1, col])
    index1.append([row, col - 1])
    index1.append([row, col + 1])
    for index in index1:
        if valid(size, index[0], index[1]):
            index2.append(l)
    return index2

def valid(size, row, col):
    if  row < 0 or col < 0 or row >= size or col >= size :
        return False
    return True

def update_found_with_info(map, probability_found, info):
    type1, type2 = info, info
    for i in range(len(map)):
        for j in range(len(map)):
            if map[i, j] == type1 or map[i, j] == type2:
                probability_found[i, j] = 0
            else:
                probability_found[i, j] = 0
    return probability_found

def list_contains_type(board, l, type):
    for i, j in l:
        if board[i, j] == type:
            return True
    return False

def sum_map(map):
    size = len(map)
    sum_map = [[0] * size for i in range(size)]
    for i in range(size):
        for j in range(size):
            if i > 0 and j >0 and i < size - 1 and j < size -1 :
                sum_map[i, j] = map[i-1, j] + map[i, j-1] + map[i+1, j] + map[i, j+1]
            elif i==0 and j==0:
                sum_map[i, j] = map[i + 1, j] + map[i, j + 1]
            elif i==dim-1 and j == dim -1:
                sum_map[i, j] = map[i-1, j] + map[i, j-1]
            elif i==0 and j == dim -1:
                sum_map[i, j] =  map[i, j-1] + map[i+1, j]
            elif i==dim-1 and j == 0:
                sum_map[i, j] = map[i-1, j]  + map[i, j+1]
            elif i == 0:
                sum_map[i, j] = map[i, j-1] + map[i+1, j] + map[i, j+1]
            elif i == dim-1:
                sum_map[i, j] = map[i-1, j] + map[i, j-1] + map[i, j+1]
            elif j == 0:
                sum_map[i, j] = map[i-1, j] + map[i+1, j] + map[i, j+1]
            elif j == dim-1:
                sum_map[i, j] = map[i-1, j] + map[i, j-1] + map[i+1, j]
    return sum_map


def update_belief_with_info(map, probability_found, info):
    type1, type2 = info, info
    length = len(probability_found)
    new_probability_found = [0] * length
    for i in range(len(map)):
        for j in range(len(map)):
            current_type = map[i, j]
            if current_type == type1:
                l = get_neighbs(len(map), i, j)
                if list_contains_type(map, l, type2):
                    new_probability_found[i, j] = 1
            elif current_type == type2:
                l = get_neighbs(len(map), i, j)
                list_contains_type(map, l, type2)
                if list_contains_type(board, l, type2):
                    new_probability_found[i, j] = 1
            else:
                new_probability_found[i, j] = 0
    sum_map = sum_map(new_probability_found)
    a = probability_found * new_probability_found
    b = [0] * len(a)
    for i in range(len(a)):
        for j in range(len(a)):
            if sum_map[i, j] > 0:
                b[i, j] = a[i, j] / sum_map[i, j]
    belief = sum_map(b)
    return belief

def target_move_rule_1(map, belief, target):
    searches = 0
    size = len(map)
    probability_found = initial.probability_found(map, belief)
    cell = get_max(probability_found)
    while (target[0],target[1]) != cell:
        belief = initial.update_belief(map, belief, cell[0], cell[1])
        searches += 1
        target, info = moving(size, target, map)
        belief = update_belief_with_info(map, probability_found, info)
        cell = get_max(belief)
    return searches

def target_move_rule_2(map, belief, target):
    searches = 0
    size = len(map)
    probability_found = initial.probability_found(map, belief)
    cell = get_max(probability_found)
    while (target[0], target[1]) != next_step:
        belief = initial.update_belief(map, belief, cell[0], cell[1])
        num_searches += 1
        probability_found = intial.probability_found(map, belief)
        target, info = moving(size, target, map)
        probability_found = update_found_with_info(map, probability_found, info)
        cell = get_max_index(probability_found)
    return searches

def target_move_do():
    for type in [0, 1, 2, 3]:
        searches_1 = 0
        searches_2 = 0
        iterations = 100
        size = 50
        for i in range(iterations):
            board = generate_board(size)
            target = initial.Target_of_Type(map, type)
            belief = initial.intial_belief(size)
            searches_1 += target_move_rule_1(map, belief, target)
            searches_2 += target_move_rule_2(map, belief, target)
        print("type :: " + str(type))
        print("Average number of searches rule-1 and rule-2 respectively: ",str(searches_1 / iterations)+"  ,  "+str(searches_2 / iterations)+"\n")
