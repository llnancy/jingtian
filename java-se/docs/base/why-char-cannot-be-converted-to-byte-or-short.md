Java的基本数据类型中的数值类型包括：byte/char/short/int/float/double/long

其数值范围如下表所示：

基本类型 | 大小 | 范围
---|:---:|:---
byte | 8 bits | [-128, +127]
char | 16 bits | ['\u0000', '\uFFFF'] 即 [0, 2^16 - 1]
short | 16 bits | [-2^15, +2^15 - 1]
int | 32 bits | [-2^31, +2^31 - 1]
float | 32 bits | IEEE754范围
double | 64 bits | IEEE754范围
long | 64 bits | [-2^63, +2^63 - 1]

注：表格中的 '[' 和 ']' 表示闭区间；IEEE754范围参考[wiki](https://en.wikipedia.org/wiki/IEEE_754-1985)

char类型的表示范围可通过以下代码查看：0-65535
```
System.out.println(Character.MIN_VALUE + 1 - 1);
System.out.println(Character.MAX_VALUE + 1 - 1);
```

byte类型只占一个字节（8比特），而char和short都占两个字节。char转成byte会出现装不下的情况，精度丢失，所以编译器不会自动进行隐式转换；

short类型虽然占两个字节，但其最大值是2^15-1，char的最大值是2^16-1，会出现转换不了的情况，所以编译器也不会进行隐式转换。

int类型是第一个数值范围比char大的类型，所以在涉及到隐式转换时，Java默认将char隐式转换成int。