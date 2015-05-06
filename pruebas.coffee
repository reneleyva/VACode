#Suma numeros en una Lista
sums = (nums...) ->
  res = 0
  nums.forEach (n) -> 
  	res += n
  return res 

#Maximo comun divisor
mcd = (m, n) ->
  if m < n
    tmp = n 
    n = m
    m = tmp
    
  r = m % n 
  if (r == 0)
    #String interpolation como ruby
    return "Maximo comun divisor de #{m} y #{n} es: #{n}"
  return mcd(n, r)

#Listas por comprension muy muy pro, es como python 
##alert person for person in ["Roger", "Ta-ku", "Slow magic"] when person[0] is "T"
#Iterar sobre objetos con of
names = sam: "seaborn", donna: "moss"
##alert("#{first} #{last}") for first, last of names
obj = null

class Vector
  constructor: (x, y) ->
    @x = x
    @y = y
  
  plus: (other) ->
    return new Vector(@x + other.x , @y + other.y)
  
  toStr: -> "(#{@x}, #{@y})"

vect = new Vector(1, 2)
alert vect.plus(new Vector(1, 2)).toStr()
