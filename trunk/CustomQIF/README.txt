CustomQIF is a GUI helper application designed to help with the intelligent
import of overly simple bank account transaction exports.  It is written in Java
using the Netbeans IDE, and is aims to be as cross-platform as possible by
utilising the in-built Java functions that return the file system directory
separator, the user home directory, etc.  It currently takes one input .QIF file
and produces one output .QIF file (which can be the same file).

The Problem:
============
My former banking institution offered downloadable transaction histories in QIF
and CSV formats, and more recently in other formats such as OFX.  That's nice,
but to really make use of these in a personal finance package was taking a lot
of manual work because the transaction categories comprised of a handful of
keywords that basically just meant what basic kind of transaction it was; e.g.
XFER, CHECK, DEPOSIT, PAYMENT, REPTPMT.

My current bank, a home loan specialist offering line-of-credit style accounts
for accelerating your repayments, also offers various formats, but the level of
simplification is even greater; for QIF and OFX files they don't even offer the
basic transaction type (unless you count DEBIT/CREDIT in an OFX file as helpful)
and memos are often split into multiple "transactions" (only the first of which
has a dollar amount, and the rest have zero as the amount).

So when I went to import these into the GnuCash financial package, I could only
categorise transactions based on whether they were money in or money out, then
try to change these to a more meaningful transaction split on a transaction by
transaction basis after the import.

This Solution:
==============
I realised that my process of manually categorising (splitting) transactions was
largely a pattern recognition process.  The programmer in me said, "That's
something a computer can be taught, yes?" and I saw the light at the end of the
drudgery tunnel.

The idea of this project is to store text patterns and account names that these
should pertain to, and process an input QIF file into a more usable QIF output
file that can be then imported into packages such as GnuCash.

At the time, in March 2007, I did what I considered was a faily thorough seach
on the Internet to see if a utility of this general description already existed.
I didn't find anything remotely like it, so, being eager to do another Netbeans
Java project, I got started.

This project is currently very much focussed on my own needs, as I make
assumptions about the scope of the input file format.  In the file TODO.txt you
will find a list of things I think I ought to do, including things that would
broaden the appeal to people other than myself (or people in a very similar
circumstance, such as fellow customers of my current banking institution).

Other Solutions:
================
As I write this in December 2009, I have just done another search, this time
only on sourceforge.net, and found just one project with a similar goal, which
apparently started at least a month before I started coding -- how did I miss
that?  I don't know!  Anyway, the project is called QifCon, and it uses xsl
transforms for transformation rules.  As such it is apparently more flexible
than CustomQIF and is also written in Java, and I would recommend anyone to give
it a try, but it is currently command-line driven only, the user must write
their own xsl files, it looks like xsl transforms don't allow for creating
elements in the output file while leaving the source element intact.  Overall
my initial feeling is that the project aims are different enough for there to be
a need for both.

Eldon
December 16, 2009