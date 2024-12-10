import sys
import time
import subprocess
import select
import datetime
from scapy.all import *
import csv
#import fourletterphat as flp


time.sleep(1) #I/O I2C catchup 
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



detected_macs = []
current_time = datetime.datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
filename  = f"MacLog_{current_time}.csv"
def main():
    try:
        load()
        # flp.print_number_str("0")
        # flp.show()
        check_kill()
        set_monitor_mode_up()
        print("proceeding")
        detect_wifi_activity()
    except KeyboardInterrupt:
        sys.exit(0)

def check_kill():
    checkkill = ["sudo", "airmon-ng", "check", "kill"]
    checkkillstart = subprocess.Popen(checkkill, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if checkkillstart.returncode == 0:
        print(f"[{green}OK{reset}]check kill successfull")
    else:
        print(f"[{red}FAILED{reset}]check kill failed or no kills performed")

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
        result = subprocess.Popen("sudo", "airmon-ng", "start", "wlan1", stdout=subprocess.PIPE)
        for l in result.stdout:
            if "already" in l.decode():
                print(f"[{green}OK{reset}]INTERNET ALREADY IN MONITOR MODE AIRMON CHECKER")
            if "failed" in l.decode():
                print(f"[{red}FAILED{reset}]MONITOR MODE FAILED AIRMON CHECKER")
            if "successfully" in l.decode():
                print(f"[{green}OK{reset}]INTERFACE SET TO MONITOR MODE, FIRST CODE ATTEMPT FAILED, SET BY AIRMON")
    except subprocess.CalledProcessError:
        print("airmon error")

def detect_wifi_activity(): #mac address
    print(f"[ {green}DETECTION{reset} ]STARTING MAC ADDRESS SNIFFING")
    sniff(iface="wlan1", prn=handle_packet, store=0)

def handle_packet(pkt):
    if pkt.haslayer(Dot11):
        mac_address = pkt.addr2
        if mac_address == None:
            mac_address = "None"
            print(f"[{green}DETECTED{reset}] BEACON FOUND BUT WAS NONE")
        if mac_address not in detected_macs:
            print(f"[{blue}MAC DETECTED{reset}]MAC ADDRESS DETECTED: {mac_address}")
            detected_macs.append(mac_address)
            with open(filename, mode="a", newline="", encoding="utf-8") as file:
                csv_writer = csv.writer(file)
                csv_writer.writerow([current_time, mac_address])
            print(f"[{green}LOG{reset}] MAC ADDRESS ADDED TO LIST FOR PARSE TO LOG")

def load():
    loading_states = ['|', '/', '-', '\\']
    for _ in range(5):
        for state in loading_states:
            state = state * 4
            sys.stdout.write(f'\rLoading {state}')
            sys.stdout.flush()
            #flp.clear()
            #flp.print_str(state)
            #flp.show()
            time.sleep(0.2)
if __name__ == "__main__":
    main()
