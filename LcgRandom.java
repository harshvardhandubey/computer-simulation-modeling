import java.math.BigInteger;
public final class LcgRandom {
/*Demomainprogram,whichrunsacorrectnesscheck*/
public static void main(String[] args) {
// Use the parameters from Java's LCG RNG
final BigInteger A =
BigInteger.valueOf(25214903917L); final BigInteger
B = BigInteger.valueOf(11);
final BigInteger M = BigInteger.ONE.shiftLeft(48); // 2^48
// Choose seed and create LCG RNG
BigInteger seed = BigInteger.valueOf(System.currentTimeMillis());
LcgRandom randSlow = new LcgRandom(A, B, M, seed);
// Start testing
finalintN
=1000;
//Checkthatskippingforwardis
correct for (int i = 0; i < N; i++) {
LcgRandom randFast = new LcgRandom(A, B, M,seed);
randFast.skip(i);
if (!randSlow.getState().equals(randFast.getState()))
throw new AssertionError();
randSlow.next();}
//Checkthatbackwarditerationiscorrect
for (int i = N - 1; i >= 0; i--) {
randSlow.previous();
LcgRandom randFast = new LcgRandom(A, B, M,seed);
randFast.skip(i);
if (!randSlow.getState().equals(randFast.getState()))
throw new AssertionError();}
//Check that backward skipping is correct
for (int i = 0; i < N; i++) {
LcgRandom randFast = new LcgRandom(A, B, M,seed);
randFast.skip(-i);
if (!randSlow.getState().equals(randFast.getState()))
throw new AssertionError();
randSlow.previous();}
System.out.printf("Test passed (n=%d)%n", N); }
/* Code for LCG RNG instances */
private finalBigInteger a; //
Multiplier privatefinalBigIntegerb;
//Increment private finalBigInteger
m; //Modulus
privatefinalBigIntegeraInv; //Multiplicativeinverseof'a'modulo
m private BigInteger x; // State
// Requires a > 0, b >= 0, m > 0, 0 <= seed < m
public LcgRandom(BigInteger a, BigInteger b, BigInteger m, BigInteger seed)
{ if (a == null || b == null || m == null || seed == null)
throw new NullPointerException();
if(a.signum()!=1||b.signum()==-1||m.signum()!=1||seed.signum()==-1||seed.compareTo(m)>=0)
throw new IllegalArgumentException("Value out ofrange");
this.a=a;
this.aInv = a.modInverse(m);
this.b =b;
this.m = m;
this.x = seed;}
// Returns the raw state, with 0 <= x < m. To get a pseudorandom number
//withacertaindistribution,thevalueneedstobefurtherprocessed.
public BigInteger getState(){
return x;}
//Advancesthestatebyoneiteration.
public void next(){
x=x.multiply(a).add(b).mod(m); //x=(a*x+b)mod
m System.out.println("Next:"+x);}
//Rewindsthestatebyoneiteration.
public void previous() {
//Theintermediateresultaftersubtracting'b'maybenegative,butthemodulararithmeticiscorrect
x = x.subtract(b).multiply(aInv).mod(m); // x = (a^-1 * (x - b)) mod m
System.out.println("previous:"+x);
}
//Advances/rewindsthestatebythegivennumberofiterations.
public void skip(int n) {
if (n >= 0)
skip(a, b, BigInteger.valueOf(n));
else
skip(aInv, aInv.multiply(b).negate(), BigInteger.valueOf(n).negate());}
// Private helper method
private void skip(BigInteger a,BigInteger b,BigInteger n) {
BigInteger a1 = a.subtract(BigInteger.ONE); // a - 1
BigInteger ma= a1.multiply(m); // (a - 1) *
m
BigInteger y = a.modPow(n, ma).subtract(BigInteger.ONE).divide(a1).multiply(b); //(a^n - 1) /(a - 1) *
b,
sort of
BigIntegerz=a.modPow(n,m).multiply(x); //a^n*x,sort
of x = y.add(z).mod(m);// (y + z) mod m
System.out.println("skip"+x);}
}