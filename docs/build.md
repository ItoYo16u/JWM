## setup

linux & macos
```sh
curl -fLo cs https://git.io/coursier-cli-"$(uname | tr LD ld)"
chmod +x cs
./cs install cs
rm cs
cs setup
# this will install Java, sbt(simple build tool)
```

or via homebrew

```sh
brew install coursier/formulas/coursier
cs setup
```

Windows
```sh
bitsadmin /transfer cs-cli https://git.io/coursier-cli-windows-exe "%cd%\cs.exe"
```


enter sbt shell and run

```sh
sbt
> compile
> run
```
