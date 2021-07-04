package smile.stat.hypothesis;
import java.util.Arrays;
import smile.stat.distribution.Distribution;
import smile.math.Math;
public class KSTest {
 public double d;
 * P-value
 */
 public double pvalue;
 private KSTest(double d, double pvalue) {
 this.d = d;
 this.pvalue = pvalue;
 }
 private static double pks(double z) {
 if (z < 0.0) {
 throw new IllegalArgumentException("Invalid z: " + z);
 }
 if (z == 0.0) {
 return 0.0;
 }
 if (z < 1.18) {
 double y = Math.exp(-1.23370055013616983 / (z * z));
 return 2.25675833419102515 * Math.sqrt(-Math.log(y)) * (y + Math.pow(y, 9) + Math.pow(y, 25) +
Math.pow(y, 49));
 } else {
 double x = Math.exp(-2. * (z * z));
 return 1. - 2. * (x - Math.pow(x, 4) + Math.pow(x, 9));
 }
 }
 private static double qks(double z) {
 if (z < 0.0) {
 throw new IllegalArgumentException("Invalid z: " + z);
 }
 if (z == 0.0) {
 return 1.0;
 }
 if (z < 1.18) {
 return 1.0 - pks(z);
 }
 double x = Math.exp(-2. * (z * z));
 return 2. * (x - Math.pow(x, 4) + Math.pow(x, 9));
 }
 private static double invqks(double q) {
 if (q <= 0.0 || q > 1.0) {
 throw new IllegalArgumentException("Invalid q: " + q);
 }
 if (q == 1.0) {
 return 0.0;
 }
 if (q > 0.3) {
 double f = -0.392699081698724155 * Math.sqr(1. - q);
 double y = invxlogx(f);
 double t;
 do {
 double logy = Math.log(y);
 double ff = f / Math.sqr(1. + Math.pow(y, 4) + Math.pow(y, 12));
 double u = (y * logy - ff) / (1. + logy);
 t = u / Math.max(0.5, 1. - 0.5 * u / (y * (1. + logy)));
 y -= t;
 } while (Math.abs(t / y) > 1.e-15);
 return 1.57079632679489662 / Math.sqrt(-Math.log(y));
 } else {
 double x = 0.03;
 double xp;
 do {
 xp = x;
 x = 0.5 * q + Math.pow(x, 4) - Math.pow(x, 9);
 if (x > 0.06) {
 x += Math.pow(x, 16) - Math.pow(x, 25);
 }
 } while (Math.abs((xp - x) / x) > 1.e-15);
 return Math.sqrt(-0.5 * Math.log(x));
 }
 }
 static double invpks(double p) {
 return invqks(1.0 - p);
 }
 private static double invxlogx(double y) {
 final double ooe = 0.367879441171442322;
 double t, u, to = 0.;
 if (y >= 0. || y <= -ooe) {
 throw new IllegalArgumentException("Invalid y: " + y);
 }
 if (y < -0.2) {
 u = Math.log(ooe - Math.sqrt(2 * ooe * (y + ooe)));
 } else {
 u = -10.;
 }
 do {
 u += (t = (Math.log(y / u) - u) * (u / (1. + u)));
 if (t < 1.e-8 && Math.abs(t + to) < 0.01 * Math.abs(t)) {
 break;
 }
 to = t;
 } while (Math.abs(t / u) > 1.e-15);
 return Math.exp(u);
 }
 public static KSTest test(double[] x, Distribution dist) {
 Arrays.sort(x);
 int n = x.length;
 double en = n;
 double d = 0.0;
 double fo = 0.0;

 for (int j = 0; j < n; j++) {
 double fn = (j + 1) / en;
 double ff = dist.cdf(x[j]);
 double dt = Math.max(Math.abs(fo - ff), Math.abs(fn - ff));
 if (dt > d) {
 d = dt;
 }
 fo = fn;
 }
 en = Math.sqrt(en);
 double p = qks((en + 0.12 + 0.11 / en) * d);
 return new KSTest(d, p);
 }
 public static KSTest test(double[] x, double[] y) {
 Arrays.sort(x);
 Arrays.sort(y);
 int j1 = 0, j2 = 0;
 int n1 = x.length, n2 = y.length;
 double en1 = n1;
 double en2 = n2;
 double d = 0.0;
 double d1, d2, dt, fn1 = 0.0, fn2 = 0.0;
 while (j1 < n1 && j2 < n2) {
 if ((d1 = x[j1]) <= (d2 = y[j2])) {
 do {
 fn1 = ++j1 / en1;
 } while (j1 < n1 && d1 == x[j1]);
 }
 if (d2 <= d1) {
 do {
 fn2 = ++j2 / en2;
 } while (j2 < n2 && d2 == y[j2]);
 }
 if ((dt = Math.abs(fn2 - fn1)) > d) {
 d = dt;
 }
 }
 double en = Math.sqrt(en1 * en2 / (en1 + en2));
 double p = qks((en + 0.12 + 0.11 / en) * d);
 return new KSTest(d, p);
 }
}