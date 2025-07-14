; Complex Example: Multiple rungs with different operations
LD %I0.0        ; Load first input
AND %I0.1       ; AND with second input
ST %Q0.0        ; Store to first output

LD %I0.2        ; Load third input (new rung)
ORN %I0.3       ; OR with inverted fourth input
ST %Q0.1        ; Store to second output

LD %I0.4        ; Load fifth input (another rung)
ANDN %I0.5      ; AND with inverted sixth input
OR %I0.6        ; OR with seventh input
STN %Q0.2       ; Store inverted to third output
