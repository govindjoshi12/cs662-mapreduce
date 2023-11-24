import numpy as np
import matplotlib.pyplot as plt
import matplotlib.cm as cm

PLOT_DIR="./plots/"
INPUT_DIR="./mapreduce/out/"
TWEET_TIME="TweetTime"
TWEET_SLEEP="TweetSleep"
# don't need to account for multiple parts for now
filename="part-00000"


def makeBasePlot(file):
    hours = []
    amts = []
    with open(file, "r") as f:
        for line in f:
            arr = line.split()
            hours.append(int(arr[0]))
            amts.append(int(arr[1]))

    hours = np.array(hours)
    amts = np.array(amts)
    sm = cm.ScalarMappable(cmap="viridis_r")
    colors = sm.to_rgba(amts)

    plt.bar(x=hours, height=amts, color=colors)

def tweetTime():
    file = INPUT_DIR + TWEET_TIME + "/" + filename
    makeBasePlot(file)

    plt.title("Tweets per hour")
    plt.xlabel("Hour of Day (24h time)")
    plt.ylabel("Amount of Tweets")

    plt.savefig(PLOT_DIR + TWEET_TIME)
    plt.clf()

def tweetSleep():
    file = INPUT_DIR + TWEET_SLEEP + "/" + filename
    makeBasePlot(file)

    plt.title("Tweets containing the word 'sleep'")
    plt.xlabel("Hour of Day (24h time)")
    plt.ylabel("Amount of Tweets")

    plt.savefig(PLOT_DIR + TWEET_SLEEP)
    plt.clf()

if __name__ == "__main__":
   tweetTime()
   tweetSleep()