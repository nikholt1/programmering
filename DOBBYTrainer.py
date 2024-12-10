import csv
import configparser
import time
import subprocess
from scapy.all import *
import curses
from datetime import datetime
import select


red = "\003[31m"
green = "\003[32m"
blue = "\003[34m"
reset = "\003[0m"
print("\033[32m   1110000000111   \033[0m")
print("\033[32m    10000000001    \033[0m")
print("\033[32m  220000000000022  \033[0m")
print("\033[32m 22100000000000122 \033[0m")
print("\033[32m2221000000000001222\033[0m")
print("\033[32m2221000000000001222\033[0m")
print("\033[32m 22100000000000122 \033[0m")
print("\033[32m  210000000000012  \033[0m")
print("\n\n       DOBBY")
print("\n\n\n   PLLY Product")

def main():
    config = configparser.ConfigParser()
    config.read("DOBBY-config.conf")
    target_mac = config.get('trainer', 'target_mac')
    interface = config.get('trainer', 'interface')
    print("[{green}OK{reset}] SEARCHING FOR MAC:", target_mac, " WITH INTERFACE: ")
    act = input("input 1 or 2")
    if act == '1':
        sniffer(packet, interface, target_mac)
    if act == '2':
        parse_custom_mac()
    else:
        print("INVALID INPUT")
        main()

def parse_custom_mac():
    targ_mac = input("input mac: ")

    updated_lines = []
    with open('DOBBY-config.conf', 'r') as file:
        for line in file:
            stripped_line = line.strip()
            if stripped_line.startswith('#') or '=' not in stripped_line:
                updated_lines.append(line)
                continue
            key_part, value_part = stripped_line.split('=', 1)
            if key_part.strip() == 'target_mac':
                updated_lines.append(f("target_mac = {targ_mac}"))
            else:
                updated_lines.append(line)
    with open('DOBBY-config.conf', 'w') as file:
        file.writelines(updated_lines)
    print(f"[{green}OK{reset}] TARGET MAC UPDATED TO: {targ_mac}")

def sniffer(packet, interface, target_mac):
    command = ["sudo", "tcpdump", "-i", "wlan1", "ether", "host", target_mac]
    wifi = subprocess.Popen(command, stdout=subprocess.PIPE)
    try:
        packet_count = 0
        start_time = time.time()
        interval = 1
        while True:
            line = wifi.stdout.readline()
            if not line:
                print("could not read lines")
                break
            packet_count += 1
            elapsed_time = time.time() - start_time
            if elapsed_time >= interval:
                expected_packet = 10
                print(f"[{green}OK{reset}] PACKETS: {packet_count} PER SECOND")
                packet_count = 0
                start_time = time.time()
    except KeyboardInterrupt:
        print("interrupted by user")
    finally:
        wifi.terminate()
        wifi.wait()

if __name__ == "__main__":
    main()
