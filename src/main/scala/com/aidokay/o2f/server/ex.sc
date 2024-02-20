def f[A, B, C](a: A, b: B) : C = ???
def eval[A, B](a: A, f: A => B) : B = f(a)
def curryB[A, B, C](f: (A, B) => C, b: B): A => C = a => f(a, b)

def ensure[A, B](a: A, b: B): Boolean =
  f(a, b) == eval(a, curryB(f, b))
