package io.github.llnancy.jingtian.javase.java15;

/**
 * sealed、non-sealed、permits
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK15 2023/7/20
 */
public class SealedTest {
}

// sealed 修饰的类是密封类，可用 permits 关键字列举出所有子类
abstract sealed class Shape permits Circle, Rectangle, Square {}

// 子类可以被 final 修饰
final class Circle extends Shape {}

// 子类可以被 sealed 修饰，仍是一个密封类
sealed class Rectangle extends Shape permits TransparentRectangle, FilledRectangle {}

// 子类被 final 修饰
final class TransparentRectangle extends Rectangle {}

// 子类被 final 修饰
final class FilledRectangle extends Rectangle {}

// 用 non-sealed 关键字修饰，解除密封，成为普通类，可以被任意类继承。
non-sealed class Square extends Shape {}
