JMH benchmarks calculate the mean and distribution using multiple runs, presenting 
results like 17.23 ± 0.55. This tool applies the same JMH math (Student's 
t-distribution with a confidence level of 99.9%) to numbers collected outside JMH. 
For example, running "$ grep PASSED result.log | awk '{print $7}' | java -jar distrib.jar"
outputs "66.6 ± 1.22".

The tool contains stripped OpenJDK JMH sources and bundled with the Apache Commons Math3 library.

