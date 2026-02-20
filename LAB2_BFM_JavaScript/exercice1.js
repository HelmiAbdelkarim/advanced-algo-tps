const MessageUrgency = {
    CALM: "CALM",
    URGENT: "URGENT",
    AGGRESSIVE: "AGGRESSIVE",
  };

// Observers
function isUppercaseLetter(c) {
    return c >= 'A' && c <= 'Z';
  }
  
function isLetter(c) {
return (isUppercaseLetter(c) || isLowercaseLetter(c));
}

function messageLength(message) {
    return message.length;
}

function countUppercase(message) {
    let count = 0;
    for (let i = 0; i < message.length; i++) {
        if (isUppercaseLetter(message[i])) {
        count++;
        }
    }
    return count;
}

function countPunctuation(message) {
    let count = 0;
    for (let i = 0; i < message.length; i++) {
        if (message[i] === '!' || message[i] === '?') {
        count++;
        }
    }
    return count;
}

function alphaCount(message) {
    let count = 0;
    for (let i = 0; i < message.length; i++) {
        if (isLetter(message[i])) {
        count++;
        }
    }
    return count;
}

function capsRatio(message) {
    const upper = countUppercase(message);
    const alpha = alphaCount(message);
    if (alpha === 0) return 0;
    return upper / alpha;
}

function classifyUrgency(message) {
    const ratio = capsRatio(message);
    const punct = countPunctuation(message);

    if (ratio >= 0.6 || punct >= 5) {
        return MessageUrgency.AGGRESSIVE;
    } else if (ratio >= 0.3 || punct >= 3) {
        return MessageUrgency.URGENT;
    } else {
        return MessageUrgency.CALM;
    }
}

function CountRepetition(message) {
    if (message.length === 0) return false;

    let prev = message[0];
    let runCount = 1;

    for (let i = 1; i < message.length; i++) {
        if (message[i] === prev) {
        runCount++;
        if (runCount > 3) {
            return true;
        }
        } else {
        prev = message[i];
        runCount = 1;
        }
    }
    return false;
}

// Algorithm
function analyzeFriendRequest(message) {
    return {
      message,
      uppercaseCount: countUppercase(message),
      punctuationCount: countPunctuation(message),
      capsRatio: capsRatio(message),
      urgency: classifyUrgency(message),
      hasRepetition: CountRepetition(message),
    };
  }

// Examples
const examples = [
    "Hey, want to connect?",
    "PLEASE ACCEPT MY REQUEST!!!",
    "Are you free? I need to talk!!!",
    "heyyyyy noooo ???!!"
];

for (const msg of examples) {
    console.log(analyzeFriendRequest(msg));
}