# Prototype: Dummy process communication

This prototype was developed as part of a thesis: https://github.com/krissrex/ntnu-tdt4501-preproject-article.

> **Note:** It is named `ecore-tool-process` because it was aimed to be an
> arbitrary java program which used existing tooling for Ecore (such as EMF
> Compare etc). It has no *actual* dependence or reference to Ecore otherwise.

This is used in a master thesis research.  
The goal of this program is to be started as a subprocess, and then
communicated with over stdio or ports.

See
[krissrex/vscode-extension-backend-talker](https://github.com/krissrex/vscode-extension-backend-talker)
for its usage (in `/lib/ecore-tool-process.jar`).

## Usage

Start the jar file `java -jar ecore-tool-process.jar stdio` and send text to it
over stdio.

```bash
java -jar ecore-tool-process.jar <MODE> [<option> <value>...]
```

### Arguments



Argument | Descripiton | Status
---------|-------------|---------
stdio    | Use stdin and stdout to communicate. | ✅
port     | Talk over TCP port. | ✅ (Only 1 client; no multithreading)
socket   | Talk over unix sockets. | ❌

### Port

Extra arguments for port mode

Argument | Description
---------|------------
`-p PORT` | Specify what port to use. Default: 0 (randomly assign a port and print it to stdout).



