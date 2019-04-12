import os

dir_path = os.path.dirname(os.path.realpath(__file__))
res = os.walk(dir_path, topdown=True, onerror=None, followlinks=False)

summ = 0

for item in res:
	for file in item[2]:
		filename = item[0] + '/' + file
		padding = 110-len(filename)
		count = len(open(filename).readlines())
		summ += count
		print(filename+ " "*padding + str(count))

print("The grand total is {}!".format(summ))
