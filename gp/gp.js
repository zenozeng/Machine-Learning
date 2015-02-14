var atoms = [];

atoms.push('+', function(a, b) {
    return a + b;
});

atoms.push('-', function(a, b) {
    return a - b;
});

atoms.push('*', function(a, b) {
    return a * b;
});

atoms.push('/', function(a, b) {
    return a / b;
});
