# Converta

Converta is a Discord bot written in Kotlin designed to convert between different measurements and numeric values. It provides users with a simple and interactive way to perform unit conversions directly within their Discord servers.

## Features

- Convert between various units (length, weight, temperature, etc.)
- Easy-to-use Discord commands
- Fast and lightweight

## Getting Started

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/ItsSimplyLeo/converta.git
    cd converta
    ```

2. Build the project using your preferred Kotlin build tool (e.g., Gradle or Maven).

3. Create a `.env` or configuration file with your Discord bot token.

### Running the Bot

Run the bot using your build tool or by executing the compiled jar:

```bash
java -jar build/libs/converta.jar
```

### Usage

Just type `/convert` in any channel where the bot is present, and Discord will guide you with autocompletion for subcommands and unit choices.

```
/convert temperature value:32 from:F to:C
/convert length value:10 from:km to:mi
```

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License.