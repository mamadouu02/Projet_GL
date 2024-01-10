output=$(which python3)
if [[ ! -z "$output" ]]; then
    echo "python3 is installed"; 
else 
    echo "python3 is not installed"; 
    echo "installing python3"; 
    echo gl | sudo -S apt update; 
    echo gl | sudo -S apt install python3 -y; 
fi