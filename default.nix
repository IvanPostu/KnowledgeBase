{ nixpkgs ? import <nixpkgs> {} }:

with nixpkgs;

let 
  jdk8 = pkgs.jdk8;
  jdk17 = pkgs.jdk17;

in
pkgs.stdenv.mkDerivation rec {
  name = "nix-shell";
  buildInputs = [
    nodejs_21
    jdk8
    jdk17 # in order to run TestNG tests in eclipse it is required java which is greater than 8 + extension
    sqlitebrowser
  ];

  # Set the JAVA_HOME environment variable
  JAVA_HOME = "${jdk8}";

  # Define a shell script to activate the environment
  shellHook = ''
    export JAVA_HOME="$JAVA_HOME"
    export PATH="$PWD/scripts:$PATH"
    export PROJECT_DIR="$PWD"

    chmod +x $PWD/scripts/*

    # Read the dev.env file and set the environment variables
    # while IFS= read -r line; do
    #   if [ -n "$line" ]; then
    #     # export "$line"
    #   fi
    # done < dev.env
  '';
}