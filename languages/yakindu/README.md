## Yakindu Statecharts language module

Itemis Create (formerly Yakindu Statechart tools) is a toolkit for developing state machines.
The SCXML language module utilizes the Yakindu Statechart Tools parser found at
https://github.com/Yakindu/statecharts to parse XMI-based statechart files.
Although Yakindu Statechart Tools is now being developed privately,
the included parser can still be used to parse statechart files.
The statecharts are instances of the Statechart metamodel depicted in the document
found [here](https://github.com/Yakindu/statecharts/blob/master/plugins/org.yakindu.sct.model.sgraph/model/emf/ysct_mm_sgraph.pdf).

### Token Extraction

The SCXML language module has two strategies for token extraction: the SimpleYakinduTokenGenerator and the
DynamicYakinduTokenGenerator. Both strategies extract tokens for each class of the metamodel. The
DynamicYakinduTokenGenerator further extracts separate tokens based on selected attributes of the object, for
example, assigning an `INITIAL` token to an `Entry` object with a kind of `INITIAL_ENTRY`.

The set of possible tokens can be found here:
https://github.com/smjonas/JPlag/blob/statecharts/languages/yakindu/src/main/java/de/jplag/yakindu/YakinduTokenType.java

### Visualization

For visualization in the JPlag report viewer, the model files are transformed into textual representation
as a hierarchy which makes it easier to spot matching regions in the input files.

## Usage

To use the new module, add the `-l yakindu` flag in the CLI.
