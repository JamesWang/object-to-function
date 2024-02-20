## Exponential Type and Eval
#### Function Types are also called exponential types
#### a -> b  can be expressed as: b^a
```
    f: 1 -> b^a   -- f is an element of b^a
    x: 1 -> a
    y: 1 -> b
    
    (f, x) is equivalent to an element of the product of (b^a x a)
    εab :: b^a x a -> b
    
    eval:: (a => b) x a -> b
```
####  Function application is the elimination rule for function type
- when get an element of function object, the only thing we can do is apply it to an element of the argument type using ε
#### curring is a consequence of exponential types
![img.png](img.png)

````
    def f[A, B, C](a: A, b: B) : C = ???
    def eval[A, B](a: A, f: A => B) : B = f(a)
    def curryB[A, B, C](f: (A, B) -> C, b: B): A => C = a => f(a,b)
    
    f(a, b) === eval(a, curryB(f, b))
````
## Categories with terminal objects, binary products, and exponentiation are called Cartesian Closed Category(CCC)

