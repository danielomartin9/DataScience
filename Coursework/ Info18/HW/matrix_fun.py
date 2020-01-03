vals = input('Enter four numbers: ')
row1 = (float(vals[0]), float(vals[1]))
row2 = (float(vals[2]), float(vals[3]))
print('Matrix: {}, {}'.format(row1, row2))

# Calculate determinant for invertibility
determinant = (row1[0]*row2[1] - row1[1]*row2[0])

if determinant != 0:
    const = 1/determinant
    inverse = ((row2[1]*const, -row1[1]*const), 
               (-row2[0]*const, row1[0]*const))
    print('Inverse: ', inverse)
else:
    print('Matrix is non-invertible')