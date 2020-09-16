package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamExample
{
	public static void main(String[] args)
	{
		StreamExample example = new StreamExample();
		List<Fruit> fruits = new ArrayList<Fruit>();
		fruits.add(example.new Fruit("apple"));
		fruits.add(example.new Fruit("pinapple"));
		System.out.print(fruits.stream().map(Fruit::getName).collect(Collectors.toList()).contains("apple"));
		fruits.forEach(Fruit::getName);
	}

	public class Fruit
	{
		private String name;

		public Fruit(String name)
		{
			this.name = name;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Fruit other = (Fruit) obj;
			if (name == null)
			{
				if (other.name != null)
					return false;
			}
			else if ( !name.equals(other.name))
				return false;
			return true;
		}

		@Override
		public String toString()
		{
			return "Fruit [name=" + name + "]";
		}

	}

}
