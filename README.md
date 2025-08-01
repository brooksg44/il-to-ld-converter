# IEC 61131-3 IL to LD Converter

## Overview
This Clojure application converts IEC 61131-3 Instruction List (IL) programs to Ladder Diagram (LD) representation using Instaparse for parsing.

## Features
- Parse IL programs using a formal grammar
- Convert IL instructions to LD elements
- Generate a textual representation of the Ladder Diagram

## Dependencies 
- Leiningen 2.9.1
- Clojure 1.12.1
- Instaparse 1.4.12
- core.match 1.0.1

## Usage
Run the application with:
```
lein run
```

### Supported IL Operations
- LD/LDN (Load/Load Negated)
- ST/STN (Store/Store Negated)
- AND/ANDN (And/And Negated)
- OR/ORN (Or/Or Negated)

## Example
Input IL:
```
LD %I0.0    ; Load input bit 0
ANDN %I0.1  ; AND with inverted input bit 1
ST %Q0.0    ; Store result to output bit 0
```

## Running Tests
```
lein test
```

## Limitations
- Currently supports a subset of IL instructions
- Basic LD representation generation
- No graphical LD
