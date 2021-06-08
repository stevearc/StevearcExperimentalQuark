#!/usr/bin/env python
import argparse
import os
import shutil
import subprocess
import tempfile
import zlib


def main() -> None:
    """Zip and unzip TouchOSC files"""
    parser = argparse.ArgumentParser(description=main.__doc__)
    parser.add_argument(
        "action", help="What action to perform", choices=["zip", "unzip", "edit"]
    )
    parser.add_argument("file", help="The input file")
    parser.add_argument("-f", help="Overwrite the output file with no prompt")
    parser.add_argument("-o", help="The output file")
    parser.add_argument(
        "--editor",
        help="The editor program to use for the 'edit' command (default %(default)s)",
        default=get_default_editor(),
    )
    args = parser.parse_args()
    if args.action == "unzip":
        unzip(args.file, args.o, args.f)
    elif args.action == "zip":
        zipfile(args.file, args.o, args.f)
    elif args.action == "edit":
        edit(args.editor, args.file, args.o, args.f)


def change_ext(filename: str, ext: str) -> str:
    name, _ = os.path.splitext(filename)
    return name + ext


def unzip(filename: str, outfile: str = None, force: bool = False) -> None:
    if outfile is None:
        outfile = change_ext(filename, ".xml")
    if (
        os.path.exists(outfile)
        and not force
        and not promptyn("%s already exists. Overwrite?" % outfile, False)
    ):
        return
    with open(filename, "rb") as ifile:
        data = zlib.decompress(ifile.read()).decode("utf-8")
    with open(outfile, "w") as ofile:
        ofile.write(data)
    if shutil.which("xmllint"):
        data = subprocess.check_output(["xmllint", "--format", outfile]).decode("utf-8")
        with open(outfile, "w") as ofile:
            ofile.write(data)


def zipfile(filename: str, outfile: str = None, force: bool = False) -> None:
    if outfile is None:
        outfile = change_ext(filename, ".tosc")
    if (
        os.path.exists(outfile)
        and not force
        and not promptyn("%s already exists. Overwrite?" % outfile, False)
    ):
        return
    if shutil.which("xmllint"):
        data = subprocess.check_output(["xmllint", "--noblanks", filename])
    else:
        with open(filename, "rb") as ifile:
            data = ifile.read()
    with open(outfile, "wb") as ofile:
        ofile.write(zlib.compress(data))


def edit(editor: str, filename: str, outfile: str = None, force: bool = False) -> None:
    if outfile is None:
        outfile = tempfile.mktemp()
    unzip(filename, outfile, force)
    code = subprocess.call([editor, outfile])
    if code == 0:
        zipfile(outfile, filename, True)


def prompt(msg: str, default: str = None) -> str:
    """Prompt user for input"""
    while True:
        response = input(msg + " ").strip()
        if not response:
            if default is None:
                continue
            return default
        return response


def promptyn(msg: str, default: bool = None) -> bool:
    """Display a blocking prompt until the user confirms"""
    while True:
        yes = "Y" if default else "y"
        no = "n" if default or default is None else "N"
        confirm = prompt("%s [%s/%s]" % (msg, yes, no), "").lower()
        if confirm in ("y", "yes"):
            return True
        elif confirm in ("n", "no"):
            return False
        elif not confirm and default is not None:
            return default


def get_default_editor() -> str:
    if "EDITOR" in os.environ:
        return os.environ["EDITOR"]
    for candidate in ["nvim", "vim", "emacs"]:
        if shutil.which(candidate):
            return candidate
    return "nano"


if __name__ == "__main__":
    main()
