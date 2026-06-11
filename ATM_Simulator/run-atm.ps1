Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location -LiteralPath $projectRoot

javac -d out '@sources.txt'
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

java -cp out com.atm.Main
