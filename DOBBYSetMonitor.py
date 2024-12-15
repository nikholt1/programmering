import sys
import subprocess
from scapy.all import *



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

    set_monitor_mode_up()
    sys.exit(0)



def set_monitor_mode_up():
    try:
        result = subprocess.run(["sudo", "ip", "link", "set", "wlan1", "down"])
        print(f"[{green}OK{reset}]Interface successfully down")
        result = subprocess.run(["sudo", "iw", "dev", "wlan1", "set", "type", "monitor"])
        print(f"[{green}OK{reset}]Interface successfully set to monitor mode")
        result = subprocess.run(["sudo", "ip", "link", "set", "wlan1", "up"])
        print(f"[{green}OK{reset}]Interface successfully up")
        result = print("interface set to monitor mode and running")
    except subprocess.CalledProcessError:
        print(f"[{red}FAILED{reset}]Setting monitor mode failed")
    
    try:
        result = subprocess.Popen(["sudo", "airmon-ng", "start", "wlan1"], stdout=subprocess.PIPE)
        for l in result.stdout:
            if "already" in l.decode():
                print(f"[{green}OK{reset}]INTERNET ALREADY IN MONITOR MODE AIRMON CHECKER")
            if "failed" in l.decode():
                print(f"[{red}FAILED{reset}]MONITOR MODE FAILED AIRMON CHECKER")
            if "successfully" in l.decode():
                print(f"[{green}OK{reset}]INTERFACE SET TO MONITOR MODE, FIRST CODE ATTEMPT FAILED, SET BY AIRMON")
    except subprocess.CalledProcessError:
        print("airmon error")


if __name__ == "__main__":
    main()

    
