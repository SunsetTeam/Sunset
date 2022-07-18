## Here listed some Android-specific issues and tips now to avoid them.

### 1. Some Android runtimes don't support `default` methods in `interface`s
Don't call `default` methods (either as `super()` or from outside context), 
or you will get `AbstractMethodError` on some Android devices. **If you need 
to use some `default` method, override it in your class and copy-paste 
realization from interface that defined it.**

### 2. Some Android runtimes don't have `java.lang.Iterable` class
Don't use this `interface`, or you will get `NoClassDefFoundError` on some 
Android devices. **Instead, use `for()` construction or `each()` method of
`Seq` class.**