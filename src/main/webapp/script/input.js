function allowOnlyNumber(e) {
    const k = e.key;
    const v = e.target.value;
    if (k.length > 1) return true;
    if (k >= '0' && k <= '9') return true;
    if (k === '.' && !v.includes('.') && v.length > 0 && v[v.length - 1] !== "-") return true;
    if (k === '-' && v.length === 0 && !v.includes('-')) return true;
    e.preventDefault();
    return false;
}