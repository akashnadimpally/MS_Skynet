#!/bin/bash

print_structure() {
    local directory=$1
    local indent=$2

    # Find all directories and files under the specified directory
    # Then sort them to ensure directories come before files
    local items=$(find "$directory" -mindepth 1 -maxdepth 1 | sort)

    local totalItems=$(echo "$items" | wc -l)
    local count=0

    # Loop through each item found by 'find'
    echo "$items" | while read item; do
        count=$((count + 1))
        # Determine if this is the last item in the current directory
        local prefix="|--"
        if [ "$count" -eq "$totalItems" ]; then
            prefix="\`--"
        fi

        # Print the current item with appropriate indentation
        if [ -d "$item" ]; then
            # It's a directory
            echo "${indent}${prefix} $(basename "$item")/"
            # Recursive call to print its contents, increasing the indentation
            print_structure "$item" "$indent|   "
        else
            # It's a file
            echo "${indent}${prefix} $(basename "$item")"
        fi
    done
}

# Starting point of the script. Replace "." with any directory you want to start from.
print_structure "." ""
