INSERT INTO wallets (
    id,
    balance,
    available_balance,
    currency,
    status
) VALUES (
    '11111111-0009-4444-4444-000000000000',
    80500.00,
    80500.00,
    'RUB',
    'ACTIVE'
) ON CONFLICT (id) DO NOTHING;