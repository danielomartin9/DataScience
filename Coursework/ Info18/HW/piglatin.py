name = input('Enter your name: ')

for name in name.split():
    name = name[1:] + name[0] + 'ay'
    print(name.capitalize(), end=' ')