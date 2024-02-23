{ nixpkgs ? import <nixpkgs> {} }:

with nixpkgs;

let jdk = pkgs.jdk8;

in
pkgs.stdenv.mkDerivation rec {
  name = "nix-shell";
  buildInputs = [
    jdk
    sqlitebrowser
  ];

  # Set the JAVA_HOME environment variable
  JAVA_HOME = "${jdk}";

  # Define a shell script to activate the environment
  shellHook = ''
    export JAVA_HOME="$JAVA_HOME"
    export PATH="$PWD/bin:$PATH"

    chmod +x $PWD/bin/*

    # Read the dev.env file and set the environment variables
    # while IFS= read -r line; do
    #   if [ -n "$line" ]; then
    #     # export "$line"
    #   fi
    # done < dev.env
  '';
}