import sys.path
sys.path.append(EXTENSION_PATH);
from appguru.info import Console
from appguru import AGE
from appguru.graphics.swing import Window
Console.successMessage(EXTENSION_PATH,"I have been loaded. YAY!")
INTERVAL=500
def SYNCHRONIZED():
    Console.successMessage(EXTENSION_PATH,"I have been executed. YAY!")
STATUS="RUN COMPLETED"