package smile.stat.hypothesis;
import smile.math.special.Gamma;
/**
* Pearson's chi-square test, also known as the chi-square goodness-of-fit test
* or chi-square test for independence. Note that the chi-square distribution
* is only approximately valid for large sample size. If a siginficant fraction
* of bins have small numbers of counts (say, < 10), then it the statistic is
* not well approximated by a chi-square probability function.
*
* @author Haifeng Li
*/
public class ChiSqTest {
 /**
 * The degree of freedom of chisq-statistic.
 */
 public double df;
 /**
 * chi-square statistic
 */
 public double chisq;
 /**
 * p-value
 */
 public double pvalue;
 /**
 * Constructor.
 */
 private ChiSqTest(double chisq, double df, double pvalue) {
 this.chisq = chisq;
 this.df = df;
 this.pvalue = pvalue;
 }
 public static ChiSqTest test(int[] bins, double[] prob) {
 return test(bins, prob, 1);
 }
 public static ChiSqTest test(int[] bins, double[] prob, int constraints) {
 int nbins = bins.length;
 int df = nbins - constraints;
 int n = 0;
 for (int i = 0; i < nbins; i++)
 n+= bins[i];
 double chisq = 0.0;
 for (int j = 0; j < nbins; j++) {
 if (prob[j] < 0.0 || prob[j] > 1.0 || (prob[j] == 0.0 && bins[j] > 0)) {
 throw new IllegalArgumentException("Bad expected number");
 }
 if (prob[j] == 0.0 && bins[j] == 0) {
 --df;
 } else {
 double nj = n * prob[j];
 double temp = bins[j] - nj;
 chisq += temp * temp / nj;
 }
 }
 double p = Gamma.regularizedUpperIncompleteGamma(0.5 * df, 0.5 * chisq);
 return new ChiSqTest(chisq, df, p);
 }
 public static ChiSqTest test(int[] bins1, int[] bins2) {
 return test(bins1, bins2, 1);
 }
 public static ChiSqTest test(int[] bins1, int[] bins2, int constraints) {
 if (bins1.length != bins2.length) {
 throw new IllegalArgumentException("Input vectors have different size");
 }
 int nbins = bins1.length;
 int df = nbins - constraints;
 double chisq = 0.0;
 for (int j = 0; j < nbins; j++) {
 if (bins1[j] == 0 && bins2[j] == 0) {
 --df;
 } else {
 double temp = bins1[j] - bins2[j];
 chisq += temp * temp / (bins1[j] + bins2[j]);
 }
 }
 double p = Gamma.regularizedUpperIncompleteGamma(0.5 * df, 0.5 * chisq);
 return new ChiSqTest(chisq, df, p);
 }
}