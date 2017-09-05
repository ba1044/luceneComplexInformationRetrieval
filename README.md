# luceneComplexInformationRetrieval


The goal of this project is to find the following result using lucene and provided files(test200).

Assignment:


1. Use Lucene to create an index of the paragraphs of train.test200.cbor.paragraphs contained
in the \test200" dataset of the TREC Complex Answer Retrieval data.
2. Run the following queries and list the paragraph IDs and content of the top 10 results.
Q1 power nap benets
Q2 whale vocalization production of sound
Q3 pokemon puzzle league
3. Graduate students: Which scoring function are you using?
4. Graduate students: Instruct Lucene to use the scoring function used in class (see below)
then rerun the three queries and list the top 10 results.
Scoring function: score(D; q) =
P
i #fqi 2 Dg,
where #fqi 2 Dg refers to the number of times the i'th query term qi appears in document
D|also called the term frequency.
