package com.globits.covid19.test.utilities;

public class Enums {
	public enum SampleStatusEnum {
		Draft("Draft"),
		Pending("Pending"),
		Accepted("Accepted"),
//		Checking("Checking"),//Dương tính chờ xác nhận
//		Positive("Positive"),//Dương tính
//		Negative("Negative"),//Âm tính
		Rejected("Rejected"),//Mẫu không thể sử dụng
		Canceled("Canceled")//Mẫu bị hủy
		;
		
		private final String name;
		private SampleStatusEnum(final String name) {
			this.name=name;
		}
		public String getName() {
			return name;
		}		
	}
	
	public enum SampleResultEnum {
		Checking("Checking"),//Dương tính chờ xác nhận
		Positive("Positive"),//Dương tính
		Negative("Negative"),//Âm tính
		;
		
		private final String name;
		private SampleResultEnum(final String name) {
			this.name=name;
		}
		public String getName() {
			return name;
		}		
	}
	
	public enum AddressUseEnum {
	    HOME("home"),
	    WORK("work");

	    private final String name;

	    /**
	     * @param name
	     */
	    private AddressUseEnum(final String name) {
	        this.name = name;
	    }

	    public String getName() {
	        return name;
	    }
	}
	
	public enum OrganizationTypeEnum {
		Both(0),
		LabTest(1),
	    CollectSample(2),
		Isolation(3);
	    private final Integer type;

	    private OrganizationTypeEnum(final Integer type) {
	        this.type = type;
	    }

		public Integer getType() {
			return type;
		}

	}
	
	public enum SampleTestTypeEnum {
		Internal(0),//Nôi bộ
		FromExternal(1),//Từ ngoài vào
		ToExternal(2);//Mẫu gửi ra ngoài
	    private final Integer type;

	    private SampleTestTypeEnum(final Integer type) {
	        this.type = type;
	    }

		public Integer getType() {
			return type;
		}

	}
	
	public enum SampleTypeEnum {
		sampleNormal(1),
		sampleGroup(2);
	    
	    private final Integer type;

	    private SampleTypeEnum(final Integer type) {
	        this.type = type;
	    }

		public Integer getType() {
			return type;
		}

	}
	
	public enum Gender {
		male("M"),//Nam
		female("F"),//Nữ
		other("U"),//Khác
		;
		
		private final String name;
		private Gender(final String name) {
			this.name=name;
		}
		public String getName() {
			return name;
		}		
	}
}
