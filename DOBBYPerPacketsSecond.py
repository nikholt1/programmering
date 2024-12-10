import sys
import time
import subprocess
import select
#import fourletterphat as flp


time.sleep(1) #I/O I2C catchup 
red = "\003[31m"
green = "\003[32m"
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
    load()
    # flp.print_number_str("0")
    # flp.show()
    check_kill()
    set_monitor_mode_up()
    print("proceeding")
    detect_wifi_activity()

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

def detect_wifi_activity():
    command = ["sudo", "tcpdump", "-i", "wlan1"]
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
                expected_packets = 10
                percentage = (packet_count / expected_packets) * 100
                print(f"[{green}OK{reset}]packets: {packet_count} per second")
                #flp.print_number_str(packet_count)
                #flp.show()
                packet_count = 0
                start_time = time.time()
    except KeyboardInterrupt:
        print("interrupted by user")
    finally:
        wifi.terminate()
        wifi.wait()

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
if __name__ == "main":
    main()

    
