package org.usfirst.frc.team1014.robot.utilities;

import java.util.Map;

/**
 * This class is used to make maps between a key and a value, especially in autonomous.
 * 
 * @author Ryan T.
 */
public class CustomEntry<K, V> implements Map.Entry<K, V>
{
	private final K key;
	private V value;

	public CustomEntry(K key, V value)
	{
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey()
	{
		return key;
	}

	@Override
	public V getValue()
	{
		return value;
	}

	@Override
	public V setValue(V value)
	{
		V old = this.value;
		this.value = value;
		return old;
	}
}