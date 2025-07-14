# IL to LD Converter - Usage Guide

## Getting Started

### Prerequisites
- Java 11 or higher
- Leiningen 2.9.0 or higher

### Installation
1. Clone or download the project
2. Navigate to the project directory
3. Run `lein deps` to download dependencies

### Running the Application

#### GUI Mode (Recommended)
```bash
./run.sh              # Linux/Mac
# or
run.bat               # Windows
# or
lein run              # Direct leiningen
```

#### Console Mode
```bash
./run.sh --console    # Linux/Mac
# or
run.bat --console     # Windows
# or
lein run --console    # Direct leiningen
```

## Using the GUI

### Main Interface
The GUI consists of:
- **Left Panel**: IL code input area with syntax highlighting
- **Right Panel**: LD diagram output (read-only)
- **Bottom Controls**: Convert button, output format selector, and clear button
- **Status Bar**: Shows conversion status and error messages
- **Menu Bar**: File operations and help

### Output Formats
Choose from three output formats:

1. **ASCII**: Visual ladder diagram using ASCII art
2. **Detailed**: Comprehensive textual analysis
3. **Combined**: Both ASCII and detailed views

### Example Workflow
1. Enter IL code in the left panel (or load an example from File menu)
2. Select desired output format
3. Click "Convert IL to LD"
4. View the result in the right panel

## IL Syntax Support

### Supported Operations
- `LD` - Load contact (normally open)
- `LDN` - Load contact (normally closed)
- `ST` - Store to coil (normal)
- `STN` - Store to coil (normally closed)
- `AND` - Series contact (normally open)
- `ANDN` - Series contact (normally closed)
- `OR` - Parallel contact (normally open)
- `ORN` - Parallel contact (normally closed)

### Operand Types
- Memory addresses: `%I0.0`, `%Q0.1`, `%M1.5`
- Identifiers: `START_BUTTON`, `MOTOR_ON`
- Numeric values: `123`, `45.67`

### Comments
Use semicolon (`;`) for comments:
```
LD %I0.0    ; This is a comment
```

## Examples

### Basic Logic
```
LD %I0.0    ; Load input 0
AND %I0.1   ; AND with input 1
ST %Q0.0    ; Output result
```

### Multiple Rungs
```
LD %I0.0    ; First rung
ST %Q0.0

LD %I0.1    ; Second rung
ST %Q0.1
```

### Complex Logic
```
LD %I0.0        ; Start with input 0
ANDN %I0.1      ; AND NOT input 1
OR %I0.2        ; OR with input 2
ST %Q0.0        ; Store result
```

## Troubleshooting

### Common Issues

1. **"Parsing failed" Error**
   - Check IL syntax
   - Ensure proper spacing between operation and operand
   - Verify operand format

2. **JavaFX Issues**
   - Ensure Java 11+ is installed
   - On some systems, may need to install OpenJFX separately

3. **Leiningen Not Found**
   - Install Leiningen from https://leiningen.org/
   - Ensure it's in your system PATH

### Debug Mode
Run with debug output:
```bash
lein run --console
```

## File Operations

### Loading Examples
Use File → Load Example to load predefined IL code

### Saving/Loading (Future Feature)
File import/export functionality is planned for future releases

## Building Standalone JAR

Create a distributable JAR file:
```bash
lein uberjar
```

Run the JAR:
```bash
java -jar target/uberjar/il-to-ld-converter-0.1.0-SNAPSHOT-standalone.jar
```

## Development

### Running Tests
```bash
lein test
```

### REPL Development
```bash
lein repl
```

### Project Structure
```
src/
├── il_to_ld_converter/
│   ├── core.clj          # Main entry point
│   ├── parser.clj        # IL parsing logic
│   ├── converter.clj     # IL to LD conversion
│   ├── ui.clj           # GUI components
│   └── ld_visualizer.clj # LD output formatting
test/
└── il_to_ld_converter/
    └── core_test.clj     # Unit tests
examples/
├── basic_example.il      # Simple IL example
└── complex_example.il    # Advanced IL example
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

Eclipse Public License v2.0
